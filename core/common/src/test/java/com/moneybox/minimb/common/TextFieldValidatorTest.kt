package com.moneybox.minimb.common

import com.moneybox.minimb.common.utils.isEmailValid
import com.moneybox.minimb.common.utils.isNumber
import com.moneybox.minimb.common.utils.isPasswordValid
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class TextFieldValidatorTest {

    @Test
    fun `when isNumber gets called, returns true if input string is a valid number`() {
        assertTrue(isNumber("123"))
    }

    @Test
    fun `when isNumber gets called, returns false if input string is a not a valid number`() {
        assertFalse(isNumber("123sdfdf"))
    }

    @Test
    fun `when isPasswordValid gets called, returns true if input string is a valid password`() {
        assertTrue(isPasswordValid("@dfdf12"))
    }

    @Test
    fun `when isPasswordValid gets called, returns false if input string is blank`() {
        assertFalse(isPasswordValid(""))
    }
    @Test
    fun `when isPasswordValid gets called, returns false if input string length is smaller than 8`() {
        assertFalse(isPasswordValid("sdsds"))
    }
    @Test
    fun `when isPasswordValid gets called, returns false if input string dont contain atleast 1 digit`() {
        assertFalse(isPasswordValid("sdsdssdsd"))
    }
    @Test
    fun `when isPasswordValid gets called, returns false if input string dont contain atleast 1 letter`() {
        assertFalse(isPasswordValid("334344"))
    }

    @Test
    fun `when email is invalid, returns false`() {
        assertFalse(isEmailValid("sdsd"))
    }
    @Test
    fun `when email dont have @, returns false`() {
        assertFalse(isEmailValid("sdsd.com"))
    }
    @Test
    fun `when email dont have dot, returns false`() {
        assertFalse(isEmailValid("sdsd@com"))
    }

    @Test
    fun `when email is valid returns true`() {
        assertTrue(isEmailValid("sdsd@yas.com"))
    }
}