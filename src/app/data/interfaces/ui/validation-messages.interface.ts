export interface IValidationMessages {
  [key: string]: IValidationMessage[];
}

export interface IValidationMessage {
  type: string;
  message: string;
}
