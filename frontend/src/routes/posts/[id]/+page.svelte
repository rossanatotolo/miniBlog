<script lang="ts">
  import { onMount } from 'svelte';
  import { page } from '$app/stores';

  let post = null;
  let author = null;
  let loading = true;
  let error = null;

  onMount(async () => {
    try {
      const postId = $page.params.id;

      const postResponse = await fetch(`http://localhost:8080/api/posts/${postId}`);
      if (!postResponse.ok) throw new Error('Пост не найден');
      post = await postResponse.json();

      const authorResponse = await fetch(`http://localhost:8080/api/users/${post.authorId}`);
      if (authorResponse.ok) {
        author = await authorResponse.json();
      }

    } catch (err) {
      error = err.message;
    } finally {
      loading = false;
    }
  });
</script>

{#if loading}
  <p>Загрузка...</p>
{:else if error}
  <p>Ошибка: {error}</p>
{:else if post}
  <article>
    <h2>{post.title}</h2>
    <p>
      Автор:
      {#if author}
        {author.username}
      {:else}
        {post.authorId}
      {/if}
    </p>
    <p>Дата: {new Date(post.createdAt).toLocaleString()}</p>
    <div>{post.content}</div>
    <br>
    <a href="/">🔙 Вернуться назад</a>
  </article>
{/if}
