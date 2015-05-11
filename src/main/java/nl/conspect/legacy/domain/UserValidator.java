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

package nl.conspect.legacy.domain;

import nl.conspect.legacy.web.NewUserRegistrationForm;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * {@link Validator} for validating {@link User} objects.
 *
 * @author Marten Deinum
 */
public class UserValidator implements Validator {

    public boolean supports(Class clazz) {
        return clazz.equals(NewUserRegistrationForm.class);
    }

    public void validate(Object target, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "displayName", "required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "emailAddress", "required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "emailValidation", "required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "passwordValidation", "required");

        NewUserRegistrationForm registration = (NewUserRegistrationForm) target;
        if (registration.getEmailAddress() != null && registration.getEmailValidation() != null) {
            if (!registration.getEmailAddress().equals(registration.getEmailValidation())) {
                errors.reject("email.validation.failed", "Email and Email Validation aren't equal.");
            }
        }

        if (registration.getPassword() != null && registration.getPasswordValidation() != null) {
            if (!registration.getPassword().equals(registration.getPasswordValidation())) {
                errors.reject("password.validation.failed", "Password and Password Validation aren't equal.");
            }
        }

    }
}
