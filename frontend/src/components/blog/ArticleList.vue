<template>
    <aside @switchArticle="switchArticle">
        <h4 class="aside-title-font">Other Blogs</h4>
        <div class="article-item aside-item-font"
             v-for="item in articles" :key="item.id"
             @click.capture="switchArticle(item.id)"
             >
            <div class="article-title">{{ item.title }}</div>
            <div class="article-date">{{ item.date }}</div>
        </div>
    </aside>
</template>

<script>
    export default {
        name: "ArticleList",
        data() {
            return {
                articles: [
                    {id: 0, title: 'Helloworld', date: "2020 02 02", content: "this is hellowworld", requestTitle: "hello-world"},
                    {id: 1, title: 'again', date: "2020 02 02", content: "this is helloworld again", requestTitle: "hello-again"},
                ],
                currentArticle: null,

                apiRoot: "localhost:8080/blog",
            };
        },
        methods: {
            // ajax
            requestArticleList() {
                this.$http.get(this.apiRoot + "/article-list").then(response => {
                    this.articles = JSON.parse(response.body);
                });
            },
            requestArticleContent(article) {
                const title = article.title;
                this.$http.get(this.apiRoot + "/article-content&title=" + title).then(response => {
                    article.content = response.body;
                });
            },

            // events
            switchArticle(index) {
                if (this.currentArticle !== null) {
                    this.currentArticle.content = null;
                }
                this.currentArticle = this.articles[index];
                // this.requestArticleContent(this.currentArticle);
                this.$emit('updateCurrentArticle', this.currentArticle);
            }
        }
    }
</script>

<style scoped>
    aside {
        display: inline-block;
        box-shadow: 0 0 2px ;
        padding: 10px;
    }
    .article-item {
        display: flex;
        justify-content: space-between;
    }
    .article-item:hover {
        cursor: pointer;
        border-bottom: 1px black solid;
    }
    .article-item div {
        display: inline-block;
        margin: 0 10px;
    }
    aside>h4 {
        margin: 0;
        padding: 0;

        margin-bottom: 5px;
    }
</style>