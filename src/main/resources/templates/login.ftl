<#import "parts/common.ftl" as c>
<#import "parts/login.ftl" as l>


<@c.page>
Login page
    <#--${Session.SPRING_SECURITY_LAST_EXCEPTION.message!"n"}-->
    ${message!"n"}
<@l.login "/login/process"></@l.login>

<a href="/registration">Add new USER</a>
</@c.page>