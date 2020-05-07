<template>
    <div id="header-titles" :style="userStyle">
        <div class="header-title" v-for="title in titles" v-bind:key="title.id">
            <a :href="title.href">{{ title.content }}</a>
        </div>
    </div>
</template>

<script>
    export default {
        name: "HeaderTitles",
        props: {
            width: String,
        },
        data() {
            return {
                titles: [
                    { id: 0, href: "#", content: "Home"},
                    { id: 1, href: "#", content: "Blogs"},
                ]
            };
        },
        computed: {
            userStyle() {
                return {
                    '--user-width': this.width,
                }
            }
        }
    }
</script>

<style scoped>
    a {
        text-decoration: none;
        color: black;
    }

    #header-titles {
        display: flex;

        width: 60%;
    }

    .header-title {
        margin: 5px;
        font-size: 20px;
    }
</style>