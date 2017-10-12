package com.m.d.core;

import com.jfinal.config.*;
import com.jfinal.core.Controller;
import com.jfinal.core.JFinal;
import com.jfinal.kit.Prop;
import com.jfinal.kit.PropKit;
import com.jfinal.kit.StrKit;
import com.jfinal.log.Log;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.activerecord.IDataSourceProvider;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.druid.DruidPlugin;
import com.jfinal.render.ViewType;
import com.jfinal.template.Engine;
import com.m.d.common.router.RouterMapping;
import com.m.d.common.util.ClassScaner;
import com.m.d.common.util.DbDialectFactory;
import com.m.d.common.util.core.Table;
import com.m.d.common.util.core.JModelMapping;
import com.m.d.im.server.IMServerStarter;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * All rights Reserved, Designed By hxjd
 *
 * @类名: ${CLASS_NAME}
 * @包名: com.m.im.common
 * @描述: ${TODO}(用一句话描述该文件做什么)
 * @所属: 华夏九鼎
 * @日期: 2017/9/30 16:11
 * @版本: V1.0
 * @创建人：马东
 * @修改人：马东
 * @版权: 2017 hxjd Inc. All rights reserved.
 * 注意：本内容仅限于华夏九鼎内部传阅，禁止外泄以及用于其他的商业目的
 */
public class IMWebConfig extends JFinalConfig {
    private Log log = Log.getLog(IMWebConfig.class);
    @Override
    public void configConstant(Constants constants) {
        PropKit.use("jbase.properties");
        dbDialectConfig();
        constants.setEncoding("UTF-8");
        constants.setMaxPostSize(1024 * 1024 * 2000);
        constants.setDevMode(true);
        constants.setViewType(ViewType.FREE_MARKER);
        constants.setBaseUploadPath("attachment");
    }

    // 路由设置
    @SuppressWarnings("unchecked")
    @Override
    public void configRoute(Routes routes) {
        List<Class<Controller>> controllerClassList = ClassScaner.scanSubClass(Controller.class);
        if (controllerClassList != null) {
            for (Class<?> clazz : controllerClassList) {
                RouterMapping urlMapping = clazz.getAnnotation(RouterMapping.class);
                if (null != urlMapping && StringUtils.isNotBlank(urlMapping.url())) {
                    if (StrKit.notBlank(urlMapping.viewPath())) {
                        routes.add(urlMapping.url(), (Class<? extends Controller>) clazz,
                                "/WEB-INF" + urlMapping.viewPath());
                    } else {
                        routes.add(urlMapping.url(), (Class<? extends Controller>) clazz);
                    }
                }
            }
        }
    }
    public DruidPlugin createDruidPlugin() {
        Prop dbProp = PropKit.use("db.properties");
        String db_host = dbProp.get("db_host").trim();

        String db_host_port = dbProp.get("db_host_port");
        db_host_port = StringUtils.isNotBlank(db_host_port) ? db_host_port.trim() : "3306";

        String db_name = dbProp.get("db_name").trim();
        String db_user = dbProp.get("db_user").trim();
        String db_password = dbProp.get("db_password").trim();
        return DbDialectFactory.getDbDialect().createDuidPlugin(db_host, db_host_port, db_name, db_user, db_password);
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public ActiveRecordPlugin createRecordPlugin(IDataSourceProvider dsp) {
        ActiveRecordPlugin arPlugin = new ActiveRecordPlugin(dsp);
        List<Class<Model>> modelClassList = ClassScaner.scanSubClass(Model.class);
        if (modelClassList != null) {
            String tablePrefix = PropKit.use("db.properties").get("db_tablePrefix");
            tablePrefix = (StrKit.isBlank(tablePrefix)) ? "" : (tablePrefix.trim());
            for (Class<?> clazz : modelClassList) {
                Table tb = clazz.getAnnotation(Table.class);
                if (tb == null)
                    continue;
                String tname = tablePrefix + tb.tableName();
                if (StringUtils.isNotBlank(tb.primaryKey())) {
                    arPlugin.addMapping(tname, tb.primaryKey(), (Class<? extends Model<?>>) clazz);
                } else {
                    arPlugin.addMapping(tname, (Class<? extends Model<?>>) clazz);
                }

                JModelMapping.me().mapping(clazz.getSimpleName().toLowerCase(), tname);
            }
        }

        arPlugin.setShowSql(JFinal.me().getConstants().getDevMode());
        return arPlugin;
    }
    @Override
    public void configEngine(Engine engine) {

    }

    @Override
    public void configPlugin(Plugins plugins) {
        DruidPlugin druidPlugin = createDruidPlugin();
        plugins.add(druidPlugin);

        ActiveRecordPlugin activeRecordPlugin = createRecordPlugin(druidPlugin);
//        activeRecordPlugin.setCache(leCachePlugin.getCache());
        activeRecordPlugin.setShowSql(JFinal.me().getConstants().getDevMode());
        plugins.add(activeRecordPlugin);

        //加载IM服务
        System.out.println("加载IM服务");
        log.info("加载IM服务。。。");
        try {
            plugins.add(new IMServerStarter());
            System.out.println("加载im成功过-----");
            log.info("IM服务加载成功。。。");
        } catch (Exception e) {
            log.debug("IM服务加载失败。。。");
        }
    }

    @Override
    public void configInterceptor(Interceptors interceptors) {

    }

    @Override
    public void afterJFinalStart() {
        //启动前端引擎
        super.afterJFinalStart();
    }

    @Override
    public void configHandler(Handlers handlers) {

    }

    private void dbDialectConfig() {
        String dialect = PropKit.get("jpress_db_dialect");
        if (StringUtils.isNotBlank(dialect)) {
            DbDialectFactory.use(dialect);
        }
    }
}
