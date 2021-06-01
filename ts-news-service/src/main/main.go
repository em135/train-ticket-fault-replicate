package main

import (
	"github.com/hoisie/web"
	//"labix.org/v2/mgo"
	//"labix.org/v2/mgo/bson"
	//"fmt"
)

//const (
//	MONGODB_URL = "ts-news-mongo:27017"
//)

//func getNews(val string) string {
//

//	session, err := mgo.Dial(MONGODB_URL)
//	if err != nil {
//		panic(err)
//	}
//	defer session.Close()
//
//	session.SetMode(mgo.Monotonic, true)
//	// db := session.DB("xtest")
//	// collection := db.C("xtest")
//	c := session.DB("xtest").C("xtest")
//

//	 var personAll []News
//	 err = c.Find(nil).All(&personAll)
//	 for i := 0; i < len(personAll); i++ {
//	 	fmt.Println("Person ", personAll[i].Name, personAll[i].Phone)
//	 }
//
//}

type News struct {
	Title   string `bson:"Title"`
	Content string `bson:"Content"`
}

func hello(ctx *web.Context, val string) string {
	var str = []byte("501 - We have been lazy with the news")
	ctx.WriteHeader(501)
	return string(str)
}

func main() {
	web.Get("/(.*)", hello)
	web.Run("0.0.0.0:12862")
}