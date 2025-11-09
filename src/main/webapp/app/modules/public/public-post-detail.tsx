import React, { useEffect, useState } from 'react';
import { useParams, Link } from 'react-router-dom';

interface Post {
  id: number;
  title?: string;
  slug?: string;
  content?: string;
  publishedAt?: string;
}

const PublicPostDetail = () => {
  const { tenantCode, slug } = useParams<{ tenantCode: string; slug: string }>();
  const [post, setPost] = useState<Post | null>(null);

  useEffect(() => {
    if (!tenantCode || !slug) return;
    fetch(`/api/public/${tenantCode}/posts/${slug}`)
      .then(res => {
        if (res.status === 404) {
          return null;
        }
        return res.json();
      })
      .then(setPost)
      .catch(console.error);
  }, [tenantCode, slug]);

  if (!tenantCode) return <div>Missing tenant</div>;
  if (!post) return <div>Đang tải hoặc không tìm thấy bài viết.</div>;

  return (
    <div className="container mt-4">
      <p>
        <Link to={`/t/${tenantCode}`}>&laquo; Về trang chủ</Link>
      </p>
      <h1>{post.title}</h1>
      {post.publishedAt && <p className="text-muted">Đăng lúc {new Date(post.publishedAt).toLocaleString()}</p>}
      <div className="mt-3" dangerouslySetInnerHTML={{ __html: post.content || '' }} />
    </div>
  );
};

export default PublicPostDetail;
