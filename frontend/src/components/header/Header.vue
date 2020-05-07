<template>
    <header>
        <component :is="'Titles'"></component>
    </header>
</template>

<script>
    import Titles from './HeaderTitles.vue'
    export default {
        name: "Header",

        components: {
            Titles,
        }
    }
</script>

<style scoped>
    header {
        width: 100%;
        padding: 15px 0;
    }
</style>