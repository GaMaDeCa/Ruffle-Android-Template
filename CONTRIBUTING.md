# Contributing to Ruffle-Android-Template

Thank you for your interest in contributing to this project!

## How to Contribute

### Reporting Issues

If you encounter any bugs or have feature requests:
1. Check if the issue already exists in the GitHub Issues
2. If not, create a new issue with:
   - Clear description of the problem
   - Steps to reproduce
   - Expected vs actual behavior
   - Device/Android version information
   - Logcat output (if applicable)

### Submitting Changes

1. **Fork the repository**
   ```bash
   git clone https://github.com/GaMaDeCa/Ruffle-Android-Template.git
   ```

2. **Create a feature branch**
   ```bash
   git checkout -b feature/your-feature-name
   ```

3. **Make your changes**
   - Follow the existing code style
   - Add comments for complex logic
   - Update documentation if needed

4. **Test your changes**
   - Build the project
   - Test on at least one Android device or emulator
   - Ensure no existing functionality is broken

5. **Commit your changes**
   ```bash
   git commit -m "Add feature: brief description"
   ```

6. **Push to your fork**
   ```bash
   git push origin feature/your-feature-name
   ```

7. **Create a Pull Request**
   - Provide a clear description of the changes
   - Link any related issues
   - Wait for review

## Code Style Guidelines

- Use 4 spaces for indentation (no tabs)
- Follow Java naming conventions:
  - Classes: PascalCase
  - Methods/Variables: camelCase
  - Constants: UPPER_SNAKE_CASE
- Add Javadoc comments for public methods and classes
- Keep methods focused and concise

## Testing

- Test on multiple Android versions if possible (minimum API 24)
- Test with different SWF files to ensure compatibility
- Check for memory leaks with long-running games
- Verify the app works on both portrait and landscape orientations

## Questions?

If you have questions about contributing, feel free to open an issue for discussion.

## License

By contributing, you agree that your contributions will be licensed under the same license as the project.
