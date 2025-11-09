import { ITenant } from 'app/shared/model/tenant.model';
import { IPost } from 'app/shared/model/post.model';

export interface ITag {
  id?: number;
  name?: string;
  slug?: string | null;
  tenant?: ITenant | null;
  posts?: IPost[] | null;
}

export const defaultValue: Readonly<ITag> = {};
