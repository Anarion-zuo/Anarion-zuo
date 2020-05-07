<template>
    <div id="blog-display">
        <Article v-bind:currentArticle="currentArticle"/>
        <aside>
            <TableOfContent/>
            <ArticleList @updateCurrentArticle="updateArticle"/>
        </aside>

    </div>
</template>

<script>
    import Article from "@/components/blog/Article";
    import ArticleList from "@/components/blog/ArticleList";
    import "vue-resource";
    import TableOfContent from "@/components/blog/TableOfContent";
    // Vue.http.options.root = "localhost:8080";
    export default {
        name: "Blog",
        components: {
            TableOfContent,
            ArticleList,
            Article,
        },
        data() {
            return {
                currentArticle: {id: 0, title: 'Helloworld', date:'2020 02 02', content: 'this is hellowworld'},
            }
        },
        methods: {
            updateArticle(article) {
                this.currentArticle = article;
            }
        }
    }
</script>

<style scoped>
    #blog-display {
        display: flex;
        justify-content: space-around;
    }

    aside {
        display: flex;
        flex-direction: column;
    }

    aside>* {
        margin: 20px 0;
    }

</style>