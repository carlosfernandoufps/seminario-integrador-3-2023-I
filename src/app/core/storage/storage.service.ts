import { decrypt, encrypt } from '@core/helpers/cryptojs.helper';
import { environment } from '@env/environment';

export abstract class StorageService implements Storage {

  constructor(
    protected readonly api: Storage
  ) { }

  get length(): number {
    return this.api.length;
  }

  setItem(key: string, value: unknown): void {
    let data = JSON.stringify(value);
    if (environment.encrypt) {
      data = encrypt(data);
    }
    this.api.setItem(key, data);
  }

  getItem<T>(key: string): T | null {
    const data = this.api.getItem(key);
    if (data !== null) {
      if (environment.encrypt) {
        return decrypt<T>(data);
      }
      return JSON.parse(data) as T;
    }
    return null;
  }

  clear(): void {
    this.api.clear();
  }

  key(index: number): string | null {
    return this.api.key(index);
  }

  removeItem(key: string): void {
    this.api.removeItem(key);
  }

}
