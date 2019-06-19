<#import "parts/common.ftl" as c>
<#import "parts/register_form.ftl" as r>

<@c.page>
User editor

<@r.registration "/main", false>

</@r.registration>

<div>
    <#if user.photoURL??>
        <img src="${user.photoURL}">
    </#if>
</div>

<#--<form action="/main" method="post" enctype="multipart/form-data">-->
    <#--<div>Name <input type="text" name="userName" value="${user.name}" /></div>-->
    <#--<div>Login or email <input type="text" name="userLogin" value="${user.email}"></div>-->
    <#--<div>Photo URL: <input type="text" name="photoURL" value="${user.photoURL}"/>-->
        <#--<div>or your file: <input type="file" name="file" value=""></div>-->

    <#--</div>-->
    <#--<#list roles as role>-->
        <#--<div>-->
            <#--<label><input type="checkbox" name="${role}" ${user.roles?seq_contains(role) ? string("checked", "")} />${role}</label>-->
        <#--</div>-->
    <#--</#list>-->
    <#--<input type="hidden" value="${user.id}" name="userId" />-->
    <#--<input type="hidden" value="${_csrf.token}" name="_csrf" />-->
    <#--<button type="submit">Save</button>-->
<#--</form>-->
<#--<div>-->
            <#--<#if user.photoURL??>-->
                <#--<img src="${user.photoURL}">-->
            <#--</#if>-->
<#--</div>-->
</@c.page>