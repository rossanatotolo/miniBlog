<script lang="ts">
  import { onMount } from 'svelte';

  let posts = [];
  let loading = true;
  let error = null;

  onMount(async () => {
    try {
      console.log('Fetching posts...');
      const response = await fetch('http://localhost:8080/api/posts');
      if (!response.ok) throw new Error(`HTTP error! status: ${response.status}`);
      posts = await response.json();
      console.log('Posts loaded:', posts);
    } catch (err) {
      error = err.message;
      console.error('Fetch error:', err);
    } finally {
      loading = false;
    }
  });
</script>

<svelte:head>
  <title>MiniBlog - Главная</title>
  <meta name="description" content="Добро пожаловать в MiniBlog!" />
</svelte:head>

<h1>Добро пожаловать в MiniBlog❤️</h1>

{#if loading}
  <p>Загрузка постов...</p>
{:else if error}
  <p>Ошибка: {error}</p>
{:else}
  <ul>
    {#each posts as post}
          <li>
            <a href="/posts/{post.id}">
               <h2>{post.title}</h2>
            </a>
            <p>{post.content.substring(0, 100)}...</p>
            <p>Дата: {post.createdAt} </p>
          </li>
        {/each}
      </ul>
    {/if}

