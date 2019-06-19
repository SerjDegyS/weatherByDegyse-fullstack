
<#macro registration path, isRegist>
    <form action=${path} method="post" enctype="multipart/form-data">
        <div>
            <label> User Name :
                <input type="text" name="name" value="<#if user??>${user.name}</#if>"/>
                <@error nameError!""></@error>
            </label>
        </div>

        <div>
            <label> User Email :
                <input type="text" name="email" value="<#if user??>${user.email}</#if>"/>
                <@error emailError!""></@error>
            </label>
        </div>
        <div>
            <label>Photo URL:
                <input type="text" name="photoURL" value="<#if photoURLError??>""<#else><#if user??>${user.photoURL}</#if></#if>"/>
                <@error photoURLError!""></@error>
            </label>
        </div>
        <div>
            <label>Or your file:
                <input type="file" name="file" value=""></div>
            </label>

        <#if isRegist>
            <div>
                <label> Password:
                    <input type="password" name="password"/>
                    <@error passwordError!""></@error>
                </label>
            </div>
            <div>
                <label> Repeat password:
                    <input type="password" name="password2"/>
                    <@error password2Error!""></@error>
                </label>
            </div>
        <#else>
            </div>
            <#list roles as role>
                <div>
                    <label><input type="checkbox" name="${role}" ${user.roles?seq_contains(role) ? string("checked", "")} />${role}</label>
                </div>
            </#list>
            <input type="hidden" value="${user.id}" name="userId" />
        </#if>
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        <div><input type="submit" value="Sign In"/></div>
    </form>
</#macro>

<#macro error pathError>
    <#if pathError??>
        <div style="color: red">
            ${pathError}
        </div
    </#if>
</#macro>