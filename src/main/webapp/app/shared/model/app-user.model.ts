import { IUser } from 'app/shared/model/user.model';
import { ITenant } from 'app/shared/model/tenant.model';

export interface IAppUser {
  id?: number;
  fullName?: string;
  user?: IUser | null;
  tenant?: ITenant | null;
}

export const defaultValue: Readonly<IAppUser> = {};
