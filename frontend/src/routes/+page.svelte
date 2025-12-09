<script lang="ts">
import { onMount } from 'svelte';

  interface Post {
    id: number;
    title: string;
    content: string;
    author: { id: number; email: string; username: string; };
    createdAt: string;
    updatedAt: string;
  }

  let posts: Post[] = [];
  let loading = true;
  let error: string | null = null;

  // Захардкоженные данные для теста (позже заменим на fetch из API)
  const featuredPosts: Post[] = [
    { id: 1, title: 'Первый пост', excerpt: 'Краткое описание первого поста.', date: '2023-10-01' },
    { id: 2, title: 'Второй пост', excerpt: 'Краткое описание второго поста.', date: '2023-10-02' },
    // Добавь больше постов по необходимости
  ];

</script>

<svelte:head>
  <title>MiniBlog - Главная</title>
  <meta name="description" content="Добро пожаловать в MiniBlog!" />
</svelte:head>

<main class="container mx-auto px-4 py-8">
<!-- About Section -->
  <section class="bg-gray-100 p-8 rounded-lg">
    <h1 class="text-3xl font-bold mb-4">MiniBlog — это веб-приложение, построенное на SvelteKit и Spring Boot.</h1>
    <p class="text-blue-700">
      Пользователи могут писать посты на различные темы.
    </p>
  </section>
  <!-- Posts -->
  <section class="mb-12">
    <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
      {#each featuredPosts as post}
        <article class="bg-white shadow-lg rounded-lg p-6 hover:shadow-xl transition-shadow">
          <h2 class="text-xl font-semibold mb-2">{post.title}</h2>
          <p class="text-gray-600 mb-4">{post.excerpt}</p>
          <p class="text-sm text-gray-500">Дата: {post.date}</p>
          <a href="/posts/{post.id}" class="text-blue-500 hover:underline">Читать далее</a>
        </article>
      {/each}
    </div>
  </section>
</main>
