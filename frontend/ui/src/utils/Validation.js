export const validateText = (dataName, fieldValue, setFormErrors) => {
  const regex = /^[a-zA-Z]+(\s[A-Za-z]*)?$/;
  const regex2 = /^[6-9][0-9]{9}$/;
  if (dataName === "guideFirstName") {
    const valid1 = regex.test(fieldValue);
    setFormErrors((prevErrors) => ({
      ...prevErrors,
      [dataName]: valid1 ? "" : `Enter a valid ${dataName.slice(10)}.`,
    }));
  } else if (dataName === "guideLastName") {
    const valid2 = regex.test(fieldValue);
    setFormErrors((prevErrors) => ({
      ...prevErrors,
      [dataName]: valid2 ? "" : `Enter a valid ${dataName.slice(9)}`,
    }));
  } else {
    const valid3 = regex2.test(fieldValue);
    setFormErrors((prevErrors) => ({
      ...prevErrors,
      [dataName]: valid3
        ? ""
        : `${dataName.slice(
            5
          )} must be a 10 digit no starting with either 6,7,8 or 9.`,
    }));
  }
};

export const validateNo = (fieldName, fieldValue, setFormErrors) => {
  const valid = /^[1-9]\d*$/.test(fieldValue);
  if (fieldName === "capacity") {
    setFormErrors((prevErrors) => ({
      ...prevErrors,
      [fieldName]: valid ? "" : `${fieldName} should not start with 0.`,
    }));
  } else {
    setFormErrors((prevErrors) => ({
      ...prevErrors,
      [fieldName]: valid
        ? ""
        : `${fieldName.slice(14)} should not start with 0.`,
    }));
  }
};

export const validateDate = (fieldName, fieldValue, setFormErrors) => {
  const valid = /^\d{4}\-(0[1-9]|1[012])\-(0[1-9]|[12][0-9]|3[01])$/.test(
    fieldValue
  );
  setFormErrors((prevErrors) => ({
    ...prevErrors,
    [fieldName]: valid ? "" : `${fieldName.slice(5)} should be correct.`,
  }));
};

export const validateEmail = (fieldName, fieldValue, setFormErrors) => {
  const regex = /^[a-z]+[a-z0-9]+@gmail\.com$/;
  const valid = regex.test(fieldValue);
  setFormErrors((prevErrors) => ({
    ...prevErrors,
    [fieldName]: valid
      ? ""
      : `${fieldName} should not start with digit, and contain only small characters followed by @gmail.com.`,
  }));
};

export const validateTourName = (
  fieldName,
  fieldValue,
  setFormErrors,
  formData
) => {
  const regex = /^[a-zA-Z]+-[a-zA-Z]+$/;
  const regex2 = /^[a-zA-Z]+(\s[A-Za-z]*)?$/;

  if (fieldName === "name") {
    const valid1 = regex.test(fieldValue);
    setFormErrors((prevErrors) => ({
      ...prevErrors,
      [fieldName]: valid1
        ? ""
        : `Enter a valid ${fieldName} like Source-Destination.`,
    }));
  } else if (fieldName === "startAt") {
    const comp1 = formData.name;
    let valid2 = true;
    if (fieldValue === comp1.split("-")[0]) {
      valid2 = true;
    } else {
      valid2 = false;
    }
    setFormErrors((prevErrors) => ({
      ...prevErrors,
      [fieldName]: valid2 ? "" : "Name must match with the source.",
    }));
  } else {
    const comp1 = formData.name;
    let valid2 = true;
    if (fieldValue === comp1.split("-")[1]) {
      valid2 = true;
    } else {
      valid2 = false;
    }
    setFormErrors((prevErrors) => ({
      ...prevErrors,
      [fieldName]: valid2 ? "" : "Name must match with the destination.",
    }));
  }
};
