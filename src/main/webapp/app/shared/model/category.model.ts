import { ITenant } from 'app/shared/model/tenant.model';

export interface ICategory {
  id?: number;
  code?: string;
  name?: string;
  description?: string | null;
  isActive?: boolean;
  tenant?: ITenant | null;
}

export const defaultValue: Readonly<ICategory> = {
  isActive: false,
};
