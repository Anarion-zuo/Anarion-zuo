<template>
    <div id="article-display">
        <div id="article-title-line">
            <div id="article-title" class="article-title-font">{{ currentArticle.title }}</div>
            <div id="article-date">{{ currentArticle.date }}</div>
        </div>

        <div id="article-content" class="article-content-font" v-html="currentArticle.content"></div>
    </div>
</template>

<script>
    export default {
        name: "Article",
        props: ['currentArticle'],
        data() {
            return {

            };
        },
    }
</script>

<style scoped>
    @import "../../assets/css/font.css";
    #article-display {
        display: inline-block;
    }
    #article-title {
        display: inline-block;
        margin-right: 80px;
    }
    #article-title-line {
        margin-bottom: 20px;
    }

    #article-date {
        display: inline-block;
    }

    #article-content {
    }
</style>