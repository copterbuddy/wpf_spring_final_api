package com.example.wallet_transfer_service.utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

@Component
public class PatternUtil {

    // public Boolean CheckForSQLInjectionRegex(String input)
    // {
    // Boolean response = false;
    // // Regex regex = new
    // Regex(@"^.*(--|;--|;|\/\*|\*\/|@@|@|xp_|char|nchar|varchar|nvarchar|alter|begin|cast|create|cursor|declare|delete|drop|end|exec|execute|fetch|insert|kill|select|sys|sysobjects|syscolumns|table|update).*");

    // // string CheckString = input.Replace("'", "''");

    // // return regex.IsMatch(CheckString.ToLower());

    // // Pattern pattern = Pattern.compile("\\d{4}-\\d{2}-\\d{2}
    // \\d{2}:\\d{2}:\\d{2}");
    // Pattern pattern =
    // Pattern.compile("^.*(--|;--|;|\/\*|\*\/|@@|@|xp_|char|nchar|varchar|nvarchar|alter|begin|cast|create|cursor|declare|delete|drop|end|exec|execute|fetch|insert|kill|select|sys|sysobjects|syscolumns|table|update).*");
    // Matcher isCheckSqlInjection = pattern.matcher(input);
    // if (!isCheckSqlInjection.matches()) {
    // response.setErrorCode(er._001_INVALID_REQUEST);
    // log.info("kunanonLog account Controller listUserInfo dateFrom invalid");
    // }

    // return response;
    // }

    // public Boolean sql_inj(String str) {
    // String inj_str =
    // "'|and|exec|insert|select|delete|update|count|*|%|chr|mid|master|truncate|char|declare|;|or|-|+|,";
    // String[] inj_stra = inj_str.split("\\|",-1);

    // for (int i = 0; i < inj_stra.length; i++) {
    // if (str.indexOf(inj_stra[i]) >= 0) {
    // return true;
    // }
    // }
    // return false;
    // }

    private static final String SQL_TYPES = "TABLE, TABLESPACE, PROCEDURE, FUNCTION, TRIGGER, KEY, VIEW, MATERIALIZED VIEW, LIBRARY"
            + "DATABASE LINK, DBLINK, INDEX, CONSTRAINT, TRIGGER, USER, SCHEMA, DATABASE, PLUGGABLE DATABASE, BUCKET, "
            + "CLUSTER, COMMENT, SYNONYM, TYPE, JAVA, SESSION, ROLE, PACKAGE, PACKAGE BODY, OPERATOR"
            + "SEQUENCE, RESTORE POINT, PFILE, CLASS, CURSOR, OBJECT, RULE, USER, DATASET, DATASTORE, "
            + "COLUMN, FIELD, OPERATOR";

    private static final String[] SQL_REGEXPS = { "(?i)(.*)(\\b)+(OR|AND)(\\s)+(true|false)(\\s)*(.*)",
            "(?i)(.*)(\\b)+(OR|AND)(\\s)+(\\w)(\\s)*(\\=)(\\s)*(\\w)(\\s)*(.*)",
            "(?i)(.*)(\\b)+(OR|AND)(\\s)+(equals|not equals)(\\s)+(true|false)(\\s)*(.*)",
            "(?i)(.*)(\\b)+(OR|AND)(\\s)+([0-9A-Za-z_'][0-9A-Za-z\\d_']*)(\\s)*(\\=)(\\s)*([0-9A-Za-z_'][0-9A-Za-z\\d_']*)(\\s)*(.*)",
            "(?i)(.*)(\\b)+(OR|AND)(\\s)+([0-9A-Za-z_'][0-9A-Za-z\\d_']*)(\\s)*(\\!\\=)(\\s)*([0-9A-Za-z_'][0-9A-Za-z\\d_']*)(\\s)*(.*)",
            "(?i)(.*)(\\b)+(OR|AND)(\\s)+([0-9A-Za-z_'][0-9A-Za-z\\d_']*)(\\s)*(\\<\\>)(\\s)*([0-9A-Za-z_'][0-9A-Za-z\\d_']*)(\\s)*(.*)",
            "(?i)(.*)(\\b)+SELECT(\\b)+\\s.*(\\b)(.*)",
            // "(?i)(.*)(\\b)+SELECT(\\b)+\\s.*(\\b)+FROM(\\b)+\\s.*(.*)",
            "(?i)(.*)(\\b)+INSERT(\\b)+\\s.*(\\b)+INTO(\\b)+\\s.*(.*)", "(?i)(.*)(\\b)+UPDATE(\\b)+\\s.*(.*)",
            "(?i)(.*)(\\b)+DELETE(\\b)+\\s.*(\\b)+FROM(\\b)+\\s.*(.*)", "(?i)(.*)(\\b)+UPSERT(\\b)+\\s.*(.*)",
            "(?i)(.*)(\\b)+SAVEPOINT(\\b)+\\s.*(.*)", "(?i)(.*)(\\b)+CALL(\\b)+\\s.*(.*)",
            "(?i)(.*)(\\b)+ROLLBACK(\\b)+\\s.*(.*)", "(?i)(.*)(\\b)+KILL(\\b)+\\s.*(.*)",
            "(?i)(.*)(\\b)+DROP(\\b)+\\s.*(.*)",
            "(?i)(.*)(\\b)+CREATE(\\b)+(\\s)*(" + SQL_TYPES.replaceAll(",", "|") + ")(\\b)+\\s.*(.*)",
            "(?i)(.*)(\\b)+ALTER(\\b)+(\\s)*(" + SQL_TYPES.replaceAll(",", "|") + ")(\\b)+\\s.*(.*)",
            "(?i)(.*)(\\b)+TRUNCATE(\\b)+(\\s)*(" + SQL_TYPES.replaceAll(",", "|") + ")(\\b)+\\s.*(.*)",
            "(?i)(.*)(\\b)+LOCK(\\b)+(\\s)*(" + SQL_TYPES.replaceAll(",", "|") + ")(\\b)+\\s.*(.*)",
            "(?i)(.*)(\\b)+UNLOCK(\\b)+(\\s)*(" + SQL_TYPES.replaceAll(",", "|") + ")(\\b)+\\s.*(.*)",
            "(?i)(.*)(\\b)+RELEASE(\\b)+(\\s)*(" + SQL_TYPES.replaceAll(",", "|") + ")(\\b)+\\s.*(.*)",
            "(?i)(.*)(\\b)+DESC(\\b)+(\\w)*\\s.*(.*)", "(?i)(.*)(\\b)+DESCRIBE(\\b)+(\\w)*\\s.*(.*)",
            "(.*)(/\\*|\\*/|;){1,}(.*)", "(.*)(-){2,}(.*)",

    };

    private static final List<Pattern> validationPatterns = buildPatterns(SQL_REGEXPS);

    // for(Object x : list) {
    // Class<?> clazz = x.getClass();
    // Field field = clazz.getField("fieldName"); //Note, this can throw an
    // exception if the field doesn't exist.
    // Object fieldValue = field.get(x);
    // }

    // public Boolean isSqlInjectionSafeList(List obj) {

    // for(Object x : obj) {
    // Class<?> clazz = x.getClass();
    // Field field = clazz.getField("fieldName"); //Note, this can throw an
    // exception if the field doesn't exist.
    // Object fieldValue = field.get(x);
    // }

    // // if (isEmpty(dataString)) {
    // // return true;
    // // }

    // // for (Pattern pattern : validationPatterns) {
    // // if (matches(pattern, dataString)) {
    // // return false;
    // // }
    // // }
    // return true;
    // }

    public Boolean isObjectSqlInjectionSafe(final Object obj) throws Exception {
        Class<? extends Object> c1 = obj.getClass();
        Map<String, Object> map = new HashMap<String, Object>();
        Field[] fields = c1.getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            String name = fields[i].getName();
            {
                fields[i].setAccessible(true);
                Object value = fields[i].get(obj);
                map.put(name, value);
                if (value == null)
                    continue;
                if (!isStringSqlInjectionSafe(value.toString()))
                    return false;
            }
        }
        return true;
    }

    public Boolean isStringSqlInjectionSafe(String dataString) {
        if (isEmpty(dataString)) {
            return true;
        }

        for (Pattern pattern : validationPatterns) {
            if (matches(pattern, dataString)) {
                return false;
            }
        }
        return true;
    }

    private static Boolean matches(Pattern pattern, String dataString) {
        Matcher matcher = pattern.matcher(dataString);
        return matcher.matches();
    }

    private static List<Pattern> buildPatterns(String[] expressionStrings) {
        List<Pattern> patterns = new ArrayList<Pattern>();
        for (String expression : expressionStrings) {
            patterns.add(getPattern(expression));
        }
        return patterns;
    }

    private static Pattern getPattern(String regEx) {
        return Pattern.compile(regEx, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE);
    }

    private static Boolean isEmpty(CharSequence cs) {
        return cs == null || cs.length() == 0;
    }
}
