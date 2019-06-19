<#import "parts/common.ftl" as c>
<#import "parts/register_form.ftl" as r>

<@c.page>
Add new USER
<div style="color: red">
    ${message!""}
</div>
<@r.registration "/registration" true/>
</@c.page>