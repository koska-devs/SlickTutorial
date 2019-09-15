# SlickTutorial

# 概要
[Play Hello World Web Tutorial](https://github.com/playframework/play-samples/tree/2.7.x/play-scala-starter-example)
を使って作ったSlick実験用のgitレポジトリ。

このようなSQLがすでにevolutionでマイグレーションできるようにしてあります。
![er](./er.png)

# 必要環境
- docker(db環境をdocker-composeで作ってます。)
- 


# スタートの仕方
```bash
$ git clone https://github.com/koska-devs/SlickTutorial.git
$ cd SlickTutorial
$ docker-compose up
```
別ターミナルで
```bash
$ sbt run
```
その後
http:localhost:9000にアクセスするとマイグレーションしていないと言った内容のエラー画面になるのでその画面で"Apply This Script"ボタンを押してセットアップ完了。