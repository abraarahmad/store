TOMCAT Server を構成する時、context.xml に DB 接続のための Resource を設定してください。

	<Resource name="jdbc/kensyu" auth="Container" type="javax.sql.DataSource"
			driverClassName="org.h2.Driver"
			url="jdbc:h2:file:~/kensyu;MVCC=TRUE"
			username="" password="" maxActive="20" maxIdle="10" maxWait="-1" />

この設定の場合、各ユーザーのホームディレクトリに kensyu.mv.db を配置する必要があります。
データベースファイルは db フォルダの中にあるので、コピーして置いてください。
（配置先は context.xml の url さえ調整すればどこでも構いません。
　他の場所に配置した場合、絶対パスで記述してください）

なお、サーバー設定がよく分からない場合に備えて、
HUE-BookStore-config サーバー設定を同梱しています。参考にしてください。

特にコンテクストルートなどをいじらなければ
アクセスする先は http://localhost:8080/bookstore/bookstore になるはずです。

文字化けする場合には、「実行」設定の common の output エンコーディングを UTF-8 にしてください。
