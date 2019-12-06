/*
 * Tigase Utils - Utilities module
 * Copyright (C) 2004 Tigase, Inc. (office@tigase.com)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, version 3 of the License.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. Look for COPYING file in the top folder.
 * If not, see http://www.gnu.org/licenses/.
 */
package tigase.util.datetime;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import static java.time.temporal.ChronoField.*;

/**
 * @author andrzej
 */
public class TimestampHelper {

	private final java.time.format.DateTimeFormatter TIMESTAMP_ISO_DATE_TIME = new DateTimeFormatterBuilder().parseCaseInsensitive()
			.append(DateTimeFormatter.ISO_LOCAL_DATE)
			.appendLiteral('T')
			.appendValue(HOUR_OF_DAY, 2)
			.appendLiteral(':')
			.appendValue(MINUTE_OF_HOUR, 2)
			.optionalStart()
			.appendLiteral(':')
			.appendValue(SECOND_OF_MINUTE, 2)
			.appendOffsetId()
			.toFormatter();
	private final java.time.format.DateTimeFormatter TIMESTAMP_ISO_DATE_TIMEMS = new DateTimeFormatterBuilder().parseCaseInsensitive()
			.append(DateTimeFormatter.ISO_LOCAL_DATE)
			.appendLiteral('T')
			.appendValue(HOUR_OF_DAY, 2)
			.appendLiteral(':')
			.appendValue(MINUTE_OF_HOUR, 2)
			.optionalStart()
			.appendLiteral(':')
			.appendValue(SECOND_OF_MINUTE, 2)
			.optionalStart()
			.appendFraction(NANO_OF_SECOND, 0, 9, true)
			.appendOffsetId()
			.toFormatter();
	/** For parsing only. */
	private final java.time.format.DateTimeFormatter TIMESTAMP_ISO_ZONED_DATE_TIME = DateTimeFormatter.ISO_ZONED_DATE_TIME;
	private final java.time.format.DateTimeFormatter TIMESTAMP_LEGACY = new DateTimeFormatterBuilder().parseCaseInsensitive()
			.appendValue(YEAR, 4)
			.appendValue(MONTH_OF_YEAR, 2)
			.appendValue(DAY_OF_MONTH, 2)
			.appendLiteral('T')
			.appendValue(HOUR_OF_DAY, 2)
			.appendLiteral(':')
			.appendValue(MINUTE_OF_HOUR, 2)
			.optionalStart()
			.appendLiteral(':')
			.appendValue(SECOND_OF_MINUTE, 2)
			.toFormatter();

	private boolean useUTC;

	/**
	 * Creates helper configured to produce timestamps in UTC timezone.
	 *
	 * @see TimestampHelper#setUseUTC(boolean)
	 */
	public TimestampHelper() {
		this.useUTC = true;
	}

	/**
	 * Creates helper configured to produce timestamps in UTC timezone or local timezone.
	 *
	 * @param useUTC <code>true</code> to use UTC timezone, <code>false</code> to use local timezone.
	 *
	 * @see TimestampHelper#setUseUTC(boolean)
	 */
	public TimestampHelper(boolean useUTC) {
		this.useUTC = useUTC;
	}

	private String format(final DateTimeFormatter formatter, final Date time) {
		final ZonedDateTime zdt = time.toInstant().atZone(ZoneOffset.UTC);

		if (useUTC) {
			return zdt.withZoneSameInstant(ZoneOffset.UTC).format(formatter);
		} else {
			return zdt.withZoneSameInstant(ZoneId.systemDefault()).format(formatter);
		}
	}

	public String format(Date ts) {
		synchronized (TIMESTAMP_ISO_DATE_TIME) {
			return format(TIMESTAMP_ISO_DATE_TIME, ts);
		}
	}

	public String formatInLegacyDelayedDelivery(Date date) {
		Calendar now = Calendar.getInstance();
		now.setTimeZone(TimeZone.getTimeZone("GMT"));
		now.setTime(date);
		return String.format("%1$tY%1$tm%1$tdT%1$tH:%1$tM:%1$tS", now);
	}

	public String formatWithMs(Date ts) {
		synchronized (TIMESTAMP_ISO_DATE_TIMEMS) {
			return format(TIMESTAMP_ISO_DATE_TIMEMS, ts);
		}
	}

	public boolean isUseUTC() {
		return useUTC;
	}

	/**
	 * If <code>false</code> then generated timestamps will be in local timezone. In other case UTC will be used.
	 *
	 * <code>true</code> by default.
	 *
	 * @param useUTC <code>true</code> to use UTC timezone, <code>false</code> to use local timezone.
	 */
	public void setUseUTC(boolean useUTC) {
		this.useUTC = useUTC;
	}

	public Date parseTimestamp(String tmp) throws ParseException {
		if (tmp == null || tmp.isEmpty()) {
			return null;
		}
		try {
			if (!tmp.contains("-")) {
				// try to use legacy format
				synchronized (TIMESTAMP_LEGACY) {
					LocalDateTime dt = LocalDateTime.parse(tmp, TIMESTAMP_LEGACY);
					return Date.from(dt.toInstant(ZoneOffset.UTC));
				}
			} else {
				synchronized (TIMESTAMP_ISO_ZONED_DATE_TIME) {
					ZonedDateTime dt = ZonedDateTime.parse(tmp, TIMESTAMP_ISO_ZONED_DATE_TIME);
					return Date.from(dt.toInstant());
				}
			}
		} catch (DateTimeParseException e) {
			throw new ParseException(e.getMessage(), e.getErrorIndex());
		}
	}

}
