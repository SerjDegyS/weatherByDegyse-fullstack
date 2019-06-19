<#import "parts/common.ftl" as c>
<#import "parts/login.ftl" as l>

<@c.page>
<div>
    <div>
        <@l.logout />
        <span><a href="/main/userList">User list</a></span>
    <#--</div>-->
    <#--<form method="post" action="/greater">-->
        <#--<input type="text" name="uid">-->
        <#--<input type="text" name="name" placeholder="Enter name">-->
        <#--<input type="text" name="email" placeholder="Enter email">-->
        <#--<input type="text" name="photoURL" placeholder="Enter photoURL">-->
        <#--<button type="submit">Greater</button>-->
        <#--<input type="hidden" name="_csrf" value="${_csrf.token}"/>-->
    <#--</form>-->
<#--</div>-->

<div>
    <form method="get" action="/main">
        <input type="text" name="filter" value="${filter!""}">
        <button type="submit">Filter</button>
    </form>
</div>

<div>User list</div>
<#list users as user>
    <div>
        <b>${user.id}</b>
        <span>${user.uid}</span>
        <i>${user.name}</i>
        <strong>${user.email}</strong>
        <i>${user.photoURL}</i>
    <div>
    <#list user.favCities as favCity>
            <div>
                ${favCity.id}
                ${favCity.name}
                ${favCity.country}
            </div>
        <#else>
            No city
    </#list>
    <#else>
        No user
</#list>

    <#--<div>-->
        <#--<form method="post" action="addCity" enctype="application/json;charset=utf-8" content="application/json">-->
            <#--<input type="text" name="id" placeholder="city id">-->
            <#--<input type="text" name="name" placeholder="city name">-->
            <#--<input type="text" name="country" placeholder="country">-->
            <#--<button type="submit">Add</button>-->
            <#--<input type="hidden" name="_csrf" value="${_csrf.token}"/>-->
        <#--</form>-->
    <#--</div>-->

    <div>
        <form method="get" action="json">
            <button type="submit">JSON</button>
            <input type="hidden" name="_csrf" value="{{_csrf.token}}"/>
        </form>
    </div>
</@c.page>