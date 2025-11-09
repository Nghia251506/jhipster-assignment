import { ITenant } from 'app/shared/model/tenant.model';
import { ICategory } from 'app/shared/model/category.model';

export interface ISource {
  id?: number;
  name?: string;
  baseUrl?: string;
  listUrl?: string;
  listItemSelector?: string;
  linkAttr?: string;
  titleSelector?: string | null;
  contentSelector?: string | null;
  thumbnailSelector?: string | null;
  authorSelector?: string | null;
  isActive?: boolean;
  note?: string | null;
  tenant?: ITenant | null;
  category?: ICategory | null;
}

export const defaultValue: Readonly<ISource> = {
  isActive: false,
};
