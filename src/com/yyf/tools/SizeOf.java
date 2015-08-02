// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space 

package com.yyf.tools;

import java.io.*;
import java.lang.instrument.Instrumentation;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.IdentityHashMap;
import java.util.Map;

public class SizeOf
{

	private static OutputStream out;
	private static Instrumentation inst;
	private static long MIN_CLASS_SIZE_TO_LOG = 0x100000L;
	private static boolean SKIP_STATIC_FIELD = false;
	private static boolean SKIP_FINAL_FIELD = false;
	private static boolean SKIP_FLYWEIGHT_FIELD = false;
	private static boolean debug = false;
	private static String unit[] = {
		"b", "Kb", "Mb"
	};

	public SizeOf()
	{
	}

	public static void premain(String s, Instrumentation instrumentation)
	{
		inst = instrumentation;
		System.out.println("JAVAGENT: call premain instrumentation for class SizeOf");
	}

	public static long sizeOf(Object obj)
	{
		if (inst == null)
			throw new IllegalStateException("Instrumentation is null");
		if (SKIP_FLYWEIGHT_FIELD && isSharedFlyweight(obj))
			return 0L;
		else
			return inst.getObjectSize(obj);
	}

	public static String humanReadable(long l)
	{
		double d = l;
		int i;
		for (i = 0; i < 3 && d >= 1024D; i++)
			d /= 1024D;

		return (new StringBuilder()).append(d).append(unit[i]).toString();
	}

	public static long deepSizeOf(Object obj)
	{
		IdentityHashMap identityhashmap = new IdentityHashMap();
		return deepSizeOf(obj, ((Map) (identityhashmap)), 0);
	}

	/**
	 * @deprecated Method iterativeSizeOf is deprecated
	 */

	public static long iterativeSizeOf(Object obj)
		throws IllegalArgumentException, IllegalAccessException, IOException
	{
		return deepSizeOf(obj);
	}

	private static String indent(int i)
	{
		StringBuilder stringbuilder = new StringBuilder();
		for (int j = 0; j < i; j++)
			stringbuilder.append("  ");

		return stringbuilder.toString();
	}

	private static long deepSizeOf(Object obj, Map map, int i)
	{
		if (obj == null)
		{
			if (debug)
				print("null\n");
			return 0L;
		}
		long l = 0L;
		if (map.containsKey(obj))
		{
			if (debug)
				print("\n%s{ yet computed }\n", new Object[] {
					indent(i)
				});
			return 0L;
		}
		if (debug)
			print("\n%s{ %s\n", new Object[] {
				indent(i), obj.getClass().getName()
			});
		map.put(obj, null);
		l = sizeOf(obj);
		if (obj instanceof Object[])
		{
			int j = 0;
			Object aobj[] = (Object[])(Object[])obj;
			int k = aobj.length;
			for (int j1 = 0; j1 < k; j1++)
			{
				Object obj1 = aobj[j1];
				if (debug)
					print("%s [%d] = ", new Object[] {
						indent(i), Integer.valueOf(j++)
					});
				l += deepSizeOf(obj1, map, i + 1);
			}

		} else
		{
			Field afield[] = obj.getClass().getDeclaredFields();
			Field afield1[] = afield;
			int i1 = afield1.length;
			for (int k1 = 0; k1 < i1; k1++)
			{
				Field field = afield1[k1];
				field.setAccessible(true);
				Object obj2;
				try
				{
					obj2 = field.get(obj);
				}
				catch (IllegalArgumentException illegalargumentexception)
				{
					throw new RuntimeException(illegalargumentexception);
				}
				catch (IllegalAccessException illegalaccessexception)
				{
					throw new RuntimeException(illegalaccessexception);
				}
				if (isComputable(field))
				{
					if (debug)
						print("%s %s = ", new Object[] {
							indent(i), field.getName()
						});
					l += deepSizeOf(obj2, map, i + 1);
					continue;
				}
				if (debug)
					print("%s %s = %s\n", new Object[] {
						indent(i), field.getName(), obj2.toString()
					});
			}

		}
		if (debug)
			print("%s} size = %s\n", new Object[] {
				indent(i), humanReadable(l)
			});
		if (MIN_CLASS_SIZE_TO_LOG > 0L && l >= MIN_CLASS_SIZE_TO_LOG)
			print("Found big object: %s%s@%s size: %s\n", new Object[] {
				indent(i), obj.getClass().getName(), Integer.valueOf(System.identityHashCode(obj)), humanReadable(l)
			});
		return l;
	}

	private static boolean isAPrimitiveType(Class class1)
	{
		if (class1 == Boolean.TYPE)
			return true;
		if (class1 == Character.TYPE)
			return true;
		if (class1 == Byte.TYPE)
			return true;
		if (class1 == Short.TYPE)
			return true;
		if (class1 == Integer.TYPE)
			return true;
		if (class1 == Long.TYPE)
			return true;
		if (class1 == Float.TYPE)
			return true;
		if (class1 == Double.TYPE)
			return true;
		return class1 == Void.TYPE;
	}

	private static boolean isComputable(Field field)
	{
		int i = field.getModifiers();
		if (isAPrimitiveType(field.getType()))
			return false;
		if (SKIP_STATIC_FIELD && Modifier.isStatic(i))
			return false;
		return !SKIP_FINAL_FIELD || !Modifier.isFinal(i);
	}

	private static boolean isSharedFlyweight(Object obj)
	{
		if (obj instanceof Comparable)
		{
			if (obj instanceof Enum)
				return true;
			if (obj instanceof String)
				return obj == ((String)obj).intern();
			if (obj instanceof Boolean)
				return obj == Boolean.TRUE || obj == Boolean.FALSE;
			if (obj instanceof Integer)
				return obj == Integer.valueOf(((Integer)obj).intValue());
			if (obj instanceof Short)
				return obj == Short.valueOf(((Short)obj).shortValue());
			if (obj instanceof Byte)
				return obj == Byte.valueOf(((Byte)obj).byteValue());
			if (obj instanceof Long)
				return obj == Long.valueOf(((Long)obj).longValue());
			if (obj instanceof Character)
				return obj == Character.valueOf(((Character)obj).charValue());
		}
		return false;
	}

	public static void setMinSizeToLog(long l)
	{
		MIN_CLASS_SIZE_TO_LOG = l;
	}

	public static void skipFinalField(boolean flag)
	{
		SKIP_FINAL_FIELD = flag;
	}

	public static void skipStaticField(boolean flag)
	{
		SKIP_STATIC_FIELD = flag;
	}

	public static void skipFlyweightObject(boolean flag)
	{
		SKIP_FLYWEIGHT_FIELD = flag;
	}

	private static void print(String s)
	{
		try
		{
			out.write(s.getBytes());
		}
		catch (IOException ioexception)
		{
			throw new RuntimeException(ioexception);
		}
	}

	private static void print(String s, Object aobj[])
	{
		try
		{
			out.write(String.format(s, aobj).getBytes());
		}
		catch (IOException ioexception)
		{
			throw new RuntimeException(ioexception);
		}
	}

	public static void setLogOutputStream(OutputStream outputstream)
	{
		if (outputstream == null)
		{
			throw new IllegalArgumentException("Can't use a null OutputStream");
		} else
		{
			out = outputstream;
			return;
		}
	}

	public static void turnOnDebug()
	{
		debug = true;
	}

	public static void turnOffDebug()
	{
		debug = false;
	}

	static 
	{
		out = System.out;
	}
}
