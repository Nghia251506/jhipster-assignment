import { TenantStatus } from 'app/shared/model/enumerations/tenant-status.model';

export interface ITenant {
  id?: number;
  code?: string;
  name?: string;
  contactEmail?: string | null;
  contactPhone?: string | null;
  maxUsers?: number;
  status?: keyof typeof TenantStatus;
}

export const defaultValue: Readonly<ITenant> = {};
