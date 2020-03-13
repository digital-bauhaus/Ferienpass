import dayjs from 'dayjs';
import 'dayjs/locale/de';
import LocalizedFormat from 'dayjs/plugin/localizedFormat';
import isSameOrBefore from 'dayjs/plugin/isSameOrBefore';
import isSameOrAfter from 'dayjs/plugin/isSameOrAfter';

dayjs.extend(isSameOrBefore);
dayjs.extend(isSameOrAfter);
dayjs.extend(LocalizedFormat);
dayjs.locale('de');

// https://day.js.org/docs/en/display/format#localized-formats
export const SHORT_DATE_FORMAT = 'L';

export default dayjs;
