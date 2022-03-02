module.exports = {
  purge: ['./public/**/*.html', './src/**/*.{vue,js,ts,jsx,tsx}'],
  darkMode: false, // or 'media' or 'class'
  theme: {
    extend: {
      colors: {
        main: {
          100: '#6667AB',
          200: '#9C9AE3',
          300: '#F0EDFF'
        },
        sub: {
          100: '#DD5183',
          200: '#F3799E',
          300: '#FFDDE9'
        },
        alert: {
          100: '#EF2B2A',
          200: '#F65B65',
          300: '#FFB8B8'
        },
        tc: {
          100: '#000000',
          200: '#222222',
          300: '#4F4F4F',
          400: '#919191',
          500: '#FFFFFF'
        }
      }
    },
  },
  variants: {
    extend: {},
  },
  plugins: [
    require('@themesberg/flowbite/plugin')
  ],
  content: [
    "./node_modules/@themesberg/flowbite/**/*.js"
  ]
}
