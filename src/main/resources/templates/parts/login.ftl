<#macro login path>
    <form action=${path} method="post">
        <div><label> User Email : <input type="email" name="email"/> </label></div>
        <div><label> Password: <input type="password" name="password"/> </label></div>
        <input type="hidden" name="_csrf" value="${(_csrf.token)!""}"/>
        <div><input type="submit" value="Sign In"/></div>
    </form>
</#macro>





<#macro logout>
    <form action="/logout" method="post">
        <input type="submit" value="Sign Out"/>
        <input type="hidden" name="_csrf" value="${(_csrf.token)!""}"/>
    </form>
</#macro>
