<div class="docs"><p>即将支持Json 的值类型见下表：</p>

<table><thead>
<tr>
<th>值类型</th>
<th>定义</th>
<th>比较优先级权值</th>
<th>用例</th>
</tr>
</thead><tbody>
<tr>
<td>整数</td>
<td>整数<br>范围：-2147483648 至 2147483647</td>
<td>10</td>
<td><code>{ "key" : 123 }</code></td>
</tr>
<tr>
<td>长整数</td>
<td>整数<br>范围：-9223372036854775808 至 9223372036854775807<br>如果用户指定的数值无法适用于整数，则 SequoiaDB 自动将其转化为浮点型</td>
<td>10</td>
<td><code>{ "key" : 3000000000 }</code> 或<br><code>{ "key" : { "$numberLong" : "3000000000" } }</code></td>
</tr>
<tr>
<td>浮点数</td>
<td>浮点数<br>范围：-1.7E+308 至 1.7E+308</td>
<td>10</td>
<td><code>{ "key" : 123.456 }</code> 或<br><code>{ "key" : 123e+50 }</code></td>
</tr>
<tr>
<td>高精度数</td>
<td>高精度数<br>范围：小数点前最高 131072 位，小数点后最高 16383 位<br>请参考 <a href="/cn/SequoiaDB-cat_id-1519612297-edition_id-300">高精度数</a></td>
<td>10</td>
<td><code>{ "key" : { $decimal:"123.456" } }</code></td>
</tr>
<tr>
<td>字符串</td>
<td>双引号包含的字符串</td>
<td>15</td>
<td><code>{ "key" : "value" }</code></td>
</tr>
<tr>
<td>对象 ID（OID）</td>
<td>十二字节对象 ID<br>请参考 <a href="/cn/SequoiaDB-cat_id-1519612292-edition_id-300">对象 ID</a></td>
<td>35</td>
<td><code>{ "key" : { "$oid" : "123abcd00ef12358902300ef" } }</code></td>
</tr>
<tr>
<td>布尔</td>
<td>true 或者 false</td>
<td>40</td>
<td><code>{ "key" : true }</code> 或 <code>{ "key" : false }</code></td>
</tr>
<tr>
<td>日期</td>
<td>YYYY-MM-DD 的日期形式<br>范围：0000-01-01 至 9999-12-31<br>请参考 <a href="/cn/SequoiaDB-cat_id-1519612293-edition_id-300">日期</a></td>
<td>45</td>
<td><code>{ "key" : { "$date" : "2012-01-01" } }</code></td>
</tr>
<tr>
<td>时间戳</td>
<td>YYYY-MM-DD-HH.mm.ss.ffffff 的时间戳形式<br>范围：1902-01-01 00:00:00.000000 至 2037-12-31 23:59:59.999999<br>请参考 <a href="/cn/SequoiaDB-cat_id-1519612294-edition_id-300">时间戳</a></td>
<td>45</td>
<td><code>{ "key" : { "$timestamp" : "2012-01-01-13.14.26.124233" } }</code></td>
</tr>
<tr>
<td>二进制数据</td>
<td>Base64 形式的二进制数据<br>请参考 <a href="/cn/SequoiaDB-cat_id-1519612295-edition_id-300">二进制数据</a></td>
<td>30</td>
<td><code>{ "key" : { "$binary" : "aGVsbG8gd29ybGQ=", "$type" : "1" } }</code></td>
</tr>
<tr>
<td>正则表达式</td>
<td>正则表达式<br>请参考 <a href="/cn/SequoiaDB-cat_id-1519612296-edition_id-300">正则表达式</a></td>
<td>50</td>
<td><code>{ "key" : { "$regex" : "^张", "$options" : "i" } }</code></td>
</tr>
<tr>
<td>对象</td>
<td>嵌套 JSON 文档对象</td>
<td>20</td>
<td><code>{ "key" : { "subobj" : "value" } }</code></td>
</tr>
<tr>
<td>数组</td>
<td>嵌套数组对象<br>请参考 <a href="/cn/SequoiaDB-cat_id-1519612291-edition_id-300">数组</a></td>
<td>25</td>
<td><code>{ "key" : [ "abc", 0, "def" ] }</code></td>
</tr>
<tr>
<td>空</td>
<td>null</td>
<td>5</td>
<td><code>{ "key" : null }</code></td>
</tr>
<tr>
<td>最小值</td>
<td>比所有值小</td>
<td>-1</td>
<td><code>{ "key" : {"$minKey": 1 } }</code></td>
</tr>
<tr>
<td>最大值</td>
<td>比所有值大</td>
<td>127</td>
<td><code>{ "key" : {"$maxKey": 1 } }</code></td>
</tr>
</tbody></table>

<blockquote>
<p><strong>Note:</strong></p>

<ul>
<li>  不同类型字段的值进行比较时，比较优先级权值越大，该类型的值就越大。</li>
</ul>
</blockquote>
</div>