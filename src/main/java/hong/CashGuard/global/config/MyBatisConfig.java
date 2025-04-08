package hong.CashGuard.global.config;

import hong.CashGuard.global.interceptor.MyBatisInterceptor;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.type.JdbcType;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionManager;

import javax.sql.DataSource;

/**
 * packageName    : hong.CashGuard.global.config
 * fileName       : MyBatisConfig
 * author         : work
 * date           : 2025-03-27
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-03-27        work       최초 생성
 * 2025-04-08        work       jdbcTemplate 등록 로직 추가
 */
@Component
public class MyBatisConfig {

    @Value("${spring.datasource.driver-class-name}")
    private String driverClassName;

    @Value("${spring.datasource.allow-multi-query}")
    private String allowMultiQuery;

    @Value("${spring.datasource.url}")
    private String datasourceUrl;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

//    @Value("${hong.mybatis-setting.config-location}")
//    private String configLocation;                  // -> MybatisConfig 파일에서 코드 작성으로 대체

    @Value("${hong.mybatis-setting.mapper-locations}")
    private String mapperLocations;

    @Value("${hong.mybatis-setting.type-aliases-package}")
    private String typeAliasesPackage;

    /**
     * @method      dataSource
     * @author      work
     * @date        2025-03-27
     * @deacription 데이터베이스와 연결을 위한 DataSource 객체 생성
    **/
    @Bean(name = "dataSource")
    public DataSource dataSource(){
        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName(driverClassName);
        dataSourceBuilder.url(datasourceUrl + "?allowMultiQueries=" + allowMultiQuery);
        dataSourceBuilder.username(username);
        dataSourceBuilder.password(password);
        return dataSourceBuilder.build();
    }

    /**
     * @method      sqlSessionFactory
     * @author      work
     * @date        2025-03-27
     * @deacription SQL 세션을 관리
    **/
    @Bean(name = "sqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();

        // 1. DB 정보
        factoryBean.setDataSource(dataSource);

        // 2. mybatis-config.xml 위치
        // factoryBean.setConfigLocation(new PathMatchingResourcePatternResolver().getResource(configLocation));

        // 3. mapper 위치
        Resource[] resources = new PathMatchingResourcePatternResolver().getResources(mapperLocations);
        factoryBean.setMapperLocations(resources);

        // 4. dto, vo 위치
        factoryBean.setTypeAliasesPackage(typeAliasesPackage);

        // 5. plugins: interceptor
        MyBatisInterceptor MyBatisInterceptor = new MyBatisInterceptor();
        factoryBean.setPlugins(MyBatisInterceptor);

        // 6. settings
        factoryBean.setConfiguration(setConfiguration());

        return factoryBean.getObject();
    }

    /**
     * @method      setConfiguration
     * @author      work
     * @date        2025-03-27
     * @deacription MyBatis 설정을 정의
    **/
    public Configuration setConfiguration() {
        Configuration configuration = new  Configuration();
        configuration.setJdbcTypeForNull(JdbcType.VARCHAR);
        configuration.setMapUnderscoreToCamelCase(true);      // DB 컬럼명이 snake_case 일 경우에 => camelCase 값으로 자동 변환
        configuration.getTypeAliasRegistry().registerAlias("Long", Long.class);
        return configuration;
    }

    /**
     * @method      transactionManager
     * @author      work
     * @date        2025-03-27
     * @deacription DB 연결을 사용하는 Tx 관리자 생성
    **/
    @Bean(name = "transactionManager")
    public TransactionManager transactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    /**
     * @method      jdbcTemplate
     * @author      work
     * @date        2025-04-08
     * @deacription {JdbcTemplate} 등록
     *              => {dataSource}를 기반으로 동작한다.
    **/
    @Bean(name = "jdbcTemplateDataSource")
    public JdbcTemplate jdbcTemplate(@Qualifier("dataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
}
