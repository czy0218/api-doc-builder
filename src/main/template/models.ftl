<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Document</title>
    <style media="screen">
        body {
            color: #333333;
        }

        table {
            border: 1px solid #aaaaaa;
            border-collapse: collapse;
        }

        th,
        td {
            border: 1px solid #aaaaaa;
            padding: 5px 10px;
        }
    </style>
</head>

<body>
<#escape x as x?html>
<ul>
    <#list classNameList as className>
        <li><a href="#${className}">${className}</a></li>
    </#list>
</ul>

    <#list classList as class>
    <h4 id="${class.className}">${class.className}</h4>
    <table>
        <tr>
            <th>字段</th>
            <th>类型</th>
            <th>空</th>
            <th>默认</th>
            <th>注释</th>
        </tr>
        <#list class.fields as field>
            <tr>
                <td>${field.fieldName?if_exists}</td>
                <td>${field.fieldType?if_exists}</td>
                <td></td>
                <td></td>
                <td>${field.remark?if_exists}</td>
            </tr>
        </#list>
    </table>
    <br/>
    </#list>
</#escape>
</body>

</html>
