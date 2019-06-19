<#import "parts/common.ftl" as c>

<@c.page>
    <div>Hello, ${(user.principal)!"user"}</div>
    <a href="/main">Main Page</a>
    <a href="/logout" methods="post">lOGOUT</a>
</@c.page>