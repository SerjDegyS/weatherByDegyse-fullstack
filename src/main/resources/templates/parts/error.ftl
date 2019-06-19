<#macro error pathError>
    <#if pathError??>
        <div style="color: red">
            ${pathError}
        </div
    </#if>
</#macro>