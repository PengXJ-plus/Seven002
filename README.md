# eladmin

项目基于 Spring Boot 2.1.0 、 Spring boot Jpa、 Spring Security、redis、Vue的前后端分离的权限管理系统，项目采用分模块开发方式， 权限控制采用 RBAC（Role-Based Access Control，基于角色的访问控制），前端菜单支持动态路由

- eladmin-system 系统核心模块
	- config 配置跨域与静态资源
	- modules 系统相关模块
		- monitor 系统监控
		    - config 配置日志拦截器与WebSocket等
		    - domain 实体类
		    - repository 数据库操作
		    - rest 前端控制器
		    - service 业务接口
		        - impl 业务接口实现
		        - query 业务查询
        - quartz 定时任务
        - security 系统安全
	        - config  JWT的安全过滤器配置
		    - rest 用户登录授权的接口
		    - security 配置spring security
		    - service 用户登录与权限的处理
		    - utils JWT工具
    	- system 系统管理

