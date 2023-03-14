
//public class DataSourceConfig {
//    @Bean
//    public DataSource dataSource() {
//        return DataSourceBuilder.create()
//                .url("jdbc:sqlserver://localhost:1433;databaseName=mydb")
//                .username("myuser")
//                .password("mypassword")
//                .driverClassName("com.microsoft.sqlserver.jdbc.SQLServerDriver")
//                .build();
//    }
//    @Bean
//    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
//        return new JdbcTemplate(dataSource);
//    }
//}