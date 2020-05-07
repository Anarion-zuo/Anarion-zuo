<template>
    <aside>
        <h4 class="aside-title-font">Table of Content</h4>
        <div id="table-of-content-list">

        </div>
    </aside>
</template>

<script>
    export default {
        name: "TableOfContent"
    }
</script>

<style scoped>
    aside {
        display: inline-block;
        box-shadow: 0 0 2px ;
        padding: 10px;
    }
    .content-item {
        display: flex;
        justify-content: space-between;
    }
    .content-item:hover {
        cursor: pointer;
        border-bottom: 1px black solid;
    }
    .content-item div {
        display: inline-block;
        margin: 0 10px;
    }
    aside>h4 {
        margin: 0;
        padding: 0;

        margin-bottom: 5px;
    }
</style>