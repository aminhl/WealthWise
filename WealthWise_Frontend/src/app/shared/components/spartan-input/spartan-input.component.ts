import { Component, Input } from '@angular/core';
import { HlmInputDirective } from '@spartan-ng/ui-input-helm';

@Component({
  selector: 'spartan-input',
  standalone: true,
  imports: [HlmInputDirective],
  template: `<input
    class="w-96 m-3"
    hlmInput
    [type]="type"
    [placeholder]="placeholder"
  />`,
})
export class SpartanInputComponent {
  @Input() type: string = 'text';
  @Input() placeholder: string = '';
}
