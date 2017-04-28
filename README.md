# BookingRoom

## このプロジェクトに関して
Spring徹底入門 SHOEISHAの14章チュートリアルを写経したものです。
パッケージ名、一部クラス名などは自分の好みで変えています。

## 事前にインストールするもの
以下のソフトウエアをインストールしておいて下さい。
- Java 1.8
- Vitrual Box
- Vagrant
- Gradle

## 依存関係
依存するパッケージはbuild.gradleに記載済みです。
- Spring Boot 1.5.2
- JPA
- Spring Security
- PostgreSQL
- Thymeleaf
- Web
- Flyway

## データベース
PostgreSQLをVirtual Box上で動作させます。

DB名はdemodbです。以下のコマンドで確認できます。
1. $ vagrant up
2. $ vagrant ssh
3. $ psql demodb

PostgreSQLを再インストールしたい場合は以下のコマンドを実行して下さい。
$ vagrant provision

## アプリケーションの起動の仕方
ターミナルで以下のコマンドを実行して下さい。

1. $vagrant up (最初の実行時はBOX (ubuntu / trusty64)のダウンロードとPostgreSQLのインストールを実行します)
2. $gradle flywayMigrate（最初の一回だけ）
3. $gradle bootRun

localhost:8080にブラウザでアクセスして下さい。

ログイン画面が表示されます。
管理権限: admin/demo
一般権限: user/demo
でログインして下さい。




