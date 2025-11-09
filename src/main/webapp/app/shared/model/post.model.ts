import dayjs from 'dayjs';
import { ITenant } from 'app/shared/model/tenant.model';
import { ISource } from 'app/shared/model/source.model';
import { ICategory } from 'app/shared/model/category.model';
import { ITag } from 'app/shared/model/tag.model';
import { PostStatus } from 'app/shared/model/enumerations/post-status.model';

export interface IPost {
  id?: number;
  originUrl?: string;
  title?: string | null;
  slug?: string | null;
  summary?: string | null;
  content?: string | null;
  contentRaw?: string | null;
  thumbnail?: string | null;
  status?: keyof typeof PostStatus;
  publishedAt?: dayjs.Dayjs | null;
  viewCount?: number;
  tenant?: ITenant | null;
  source?: ISource | null;
  category?: ICategory | null;
  tags?: ITag[] | null;
}

export const defaultValue: Readonly<IPost> = {};
