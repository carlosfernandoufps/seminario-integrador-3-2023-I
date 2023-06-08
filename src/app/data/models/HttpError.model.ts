import { IHttpError } from "@data/interfaces/http/http-error.interface";

export class HttpError implements IHttpError {
  constructor(
    private _module: string,
    private _message: string,
    private _type: string,
  ) { }

  public get module(): string {
    return this._module;
  }

  public set module(value: string) {
    this._module = value;
  }

  public get message(): string {
    return this._message;
  }

  public set message(value: string) {
    this._message = value;
  }

  public get type(): string {
    return this._type;
  }

  public set type(value: string) {
    this._type = value;
  }

  public static fromJSON(json: any): HttpError {
    return new HttpError(json.module, json.message, json.type);
  }

  /*
  public static fromJSON(json: any): HttpError {
      return new HttpError(
          json.module,
          json.message,
          json.type,
      );
  }
   */

  public toJSON(): any {
    return {
      module: this.module,
      message: this.message,
      type: this.type,
    };
  }

}
