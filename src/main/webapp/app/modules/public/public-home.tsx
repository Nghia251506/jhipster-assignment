import React, { useEffect, useState } from 'react';
import { Link, useParams } from 'react-router-dom';

interface Post {
  id: number;
  title?: string;
  slug?: string;
  summary?: string;
  publishedAt?: string;
}

const PublicHome = () => {
  const { tenantCode } = useParams<{ tenantCode: string }>();
  const [posts, setPosts] = useState<Post[]>([]);

  useEffect(() => {
    if (!tenantCode) return;
    fetch(`/api/public/${tenantCode}/posts`)
      .then(res => res.json())
      .then(setPosts)
      .catch(console.error);
  }, [tenantCode]);

  if (!tenantCode) return <div>Missing tenant</div>;

  return (
    <div className="container mt-4">
      <h2>Tin mới - {tenantCode}</h2>
      {posts.length === 0 && <p>Chưa có bài viết nào.</p>}
      <ul className="list-unstyled">
        {posts.map(post => (
          <li key={post.id} className="mb-3">
            <h4>
              <Link to={`/t/${tenantCode}/post/${post.slug}`}>{post.title}</Link>
            </h4>
            {post.summary && <p>{post.summary}</p>}
          </li>
        ))}
      </ul>
    </div>
  );
};

export default PublicHome;
