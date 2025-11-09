import dayjs from 'dayjs';
import { ITenant } from 'app/shared/model/tenant.model';
import { CrawlStatus } from 'app/shared/model/enumerations/crawl-status.model';

export interface ICrawlLog {
  id?: number;
  url?: string;
  status?: keyof typeof CrawlStatus;
  errorMessage?: string | null;
  crawledAt?: dayjs.Dayjs;
  tenant?: ITenant | null;
}

export const defaultValue: Readonly<ICrawlLog> = {};
