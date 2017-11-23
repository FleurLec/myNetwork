devtools::install_github(repo = "mongosoup/rmongodb")


library(rmongodb)
# connect to MongoDB
mongo = mongo.create(host = "localhost")
mongo.is.connected(mongo)


# get mongo collections
mongo.get.databases(mongo)

mongo.get.database.collections(mongo, db = "MyNetWorkDB")

mongo.get.database.collections(mongo, db = mongo.get.databases(mongo)[1])
