import { ITenant } from 'app/shared/model/tenant.model';

export interface ISiteSetting {
  id?: number;
  siteTitle?: string | null;
  siteDescription?: string | null;
  siteKeywords?: string | null;
  gaTrackingId?: string | null;
  bannerTop?: string | null;
  bannerSidebar?: string | null;
  tenant?: ITenant | null;
}

export const defaultValue: Readonly<ISiteSetting> = {};
