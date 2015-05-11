/*
 * Copyright 2000-2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package nl.conspect.legacy.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author marten
 */
public abstract class DateUtils {

    public static Date parse(String s) throws ParseException {
        return new SimpleDateFormat("dd-MM-yyyy").parse(s);
    }

    public static String formatDateOnly(Date d) {
        return new SimpleDateFormat("dd-MM-yyyy").format(d);
    }

    public static String formatTimeOnly(Date d) {
        return new SimpleDateFormat("HH:mm:ss").format(d);
    }

}
