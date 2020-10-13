/**
 * This package contains Utility classes (Annotated by Lombok).
 * this annotation will make the following changes at compile time to our class:
 *      Marks the class as final.
 *      It generates a private no-arg constructor.
 *      It only allows the methods or fields to be static.
 * A Utility class, by definition, should not contain any state. Hence it is usual to put
 * shared constants or methods inside utility class so that they can be reused.
 * As they are shared and not tied to any specific object it makes sense to mark them as static.
 */
package com.example.springreddit.util;