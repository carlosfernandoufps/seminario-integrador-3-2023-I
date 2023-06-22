import { IValidationMessages } from '../interfaces/ui/validation-messages.interface';

export const VALIDACION_MENSAJES_PROYECTO: IValidationMessages = {
  titulo: [
    { type: 'required', message: 'El título es obligatorio.' },
    { type: 'minlength', message: 'El título debe tener al menos 5 caracteres.' },
    { type: 'maxlength', message: 'El título excede el tamaño permitido (30).' }
  ],
  materia: [
    { type: 'required', message: 'La materia es obligatoria.' }
  ],
  descripcion: [
    { type: 'required', message: 'La descripción es obligatoria.' },
    { type: 'minlength', message: 'La descripción debe tener al menos 10 caracteres.' },
    { type: 'maxlength', message: 'La descripción excede el tamaño permitido (100).' }
  ],
  link: [
    { type: 'required', message: 'El link es obligatorio.' }
  ],
  imagen: [
    { type: 'required', message: 'La imagen es obligatoria.' }
  ]
};
